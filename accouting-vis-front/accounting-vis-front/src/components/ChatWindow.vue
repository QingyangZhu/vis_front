<template>
  <div class="chat-window" @mouseover="handleMouseOver" @mouseleave="handleMouseLeave">
    <div class="chat-header">
      <h2>AI助手</h2>
    </div>

    <div ref="chatBox" class="chat-box">
      <div v-for="(message, index) in messages" :key="index" class="chat-message">
        <p :class="{'user-message': message.type === 'user', 'ai-message': message.type === 'ai'}">
          {{ message.text }}
        </p>
      </div>
    </div>

    <div class="input-box">
      <input v-model="userMessage" @keyup.enter="sendMessage" :disabled="loading" placeholder="请输入你的问题..." />
      <button @click="sendMessage" :disabled="loading">发送</button>
    </div>

    <div v-if="loading" class="loading">
      AI助手正在思考...
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue';
import axios from 'axios';

const userMessage = ref('');
const messages = ref([]);
const loading = ref(false);
const chatBox = ref(null);
const selectedRole = ref('finance'); // 默认角色

// 鼠标悬停放大
const handleMouseOver = () => {
  if (chatBox.value) {
    chatBox.value.parentElement.style.transform = 'scale(1.05)';
  }
};
const handleMouseLeave = () => {
  if (chatBox.value) {
    chatBox.value.parentElement.style.transform = 'scale(1)';
  }
};

// 发送消息
const sendMessage = async () => {
  try {
    if (userMessage.value.trim() === '' || loading.value) return;

    const userInput = userMessage.value.trim();
    messages.value.push({ type: 'user', text: userInput });
    userMessage.value = '';

    await nextTick();
    scrollToBottom();

    loading.value = true;

    const history = messages.value.map(m => ({
      role: m.type === 'user' ? 'user' : 'assistant',
      content: m.text
    }));

    const response = await axios.post(
        '/api/chat/message',
        {
          message: userInput,
          history: history,
          role: selectedRole.value // 暂时传过去（后端可根据需要使用）
        },
        {
          headers: {
            'Content-Type': 'application/json'
          }
        }
    );

    console.log('AI返回:', response.data);
    await simulateTyping(response.data);

  } catch (error) {
    console.error('发送过程中出错:', error.response || error.message || error);
    messages.value.push({ type: 'ai', text: '⚠️ AI助手暂时无法回复，请稍后再试。' });
  } finally {
    loading.value = false;
    await nextTick();
    scrollToBottom();
  }
};

// 模拟打字效果
const simulateTyping = async (text) => {
  if (!text) {
    messages.value.push({ type: 'ai', text: '⚠️ AI没有返回任何内容。' });
    return;
  }

  let aiReply = '';
  for (let i = 0; i < text.length; i++) {
    aiReply += text[i];
    if (i === 0) {
      messages.value.push({ type: 'ai', text: aiReply });
    } else {
      messages.value[messages.value.length - 1].text = aiReply;
    }
    await sleep(30);
  }
};

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

// 自动滚动
const scrollToBottom = () => {
  if (chatBox.value) {
    chatBox.value.scrollTop = chatBox.value.scrollHeight;
  }
};
</script>

<style scoped>
.chat-window {
  background-color: rgba(0,0,0,0.3);;
  padding: 5px;
  margin: 5px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  flex: 1 1 calc(90% - 20px);
  max-width: calc(95% - 20px);
  display: flex;
  flex-direction: column;
  height: 95%;
  transition: transform 0.2s ease-in-out;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.role-select {
  padding: 5px 10px;
  border: 1px solid rgba(73, 59, 59, 0.3);;
  border-radius: 6px;
  font-size: 14px;
}

.chat-box {
  flex-grow: 5;

  overflow-y: auto;
  padding-bottom: 10px;
}

.chat-message {
  margin: 10px 0;
}

.user-message {
  background-color: #d2f8d2;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
  margin-left: auto;
  text-align: right;
}

.ai-message {
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
  margin-right: auto;
  text-align: left;
}

.input-box {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

input {
  flex: 1;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #369d74;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  margin-top: 10px;
  color: #666;
}
</style>
