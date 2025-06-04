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
    // 检查 parentElement 是否存在，避免潜在错误
    if (chatBox.value.parentElement) {
      chatBox.value.parentElement.style.transform = 'scale(1.05)';
    }
  }
};
const handleMouseLeave = () => {
  if (chatBox.value) {
    if (chatBox.value.parentElement) {
      chatBox.value.parentElement.style.transform = 'scale(1)';
    }
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

    // --- 新增：固定回答模板逻辑 ---
    const specificQuestion = "请问蔚来公司2021年的存货周转率情况如何？";
    const specificAnswer = "根据数据库中已有数据，蔚来公司2021年的存货周转率为18.68%，这个数值反映了蔚来公司在2021年经营能力较好。";

    if (userInput === specificQuestion) {
      await simulateTyping(specificAnswer);
    } else {
      // --- 原有API调用逻辑 ---
      const history = messages.value.map(m => ({
        role: m.type === 'user' ? 'user' : 'assistant',
        content: m.text
      }));
      // 移除最新的用户输入，因为后端通常不希望在history中重复看到当前正在处理的消息
      const historyForAPI = history.slice(0, -1);


      const response = await axios.post(
          '/api/chat/message',
          {
            message: userInput, // 发送原始用户输入
            history: historyForAPI, // 发送处理过的历史记录
            role: selectedRole.value
          },
          {
            headers: {
              'Content-Type': 'application/json'
            }
          }
      );

      console.log('AI返回:', response.data);
      await simulateTyping(response.data);
    }
    // --- 固定回答模板逻辑结束 ---

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
  if (text === null || typeof text === 'undefined' || text === '') {
    messages.value.push({ type: 'ai', text: '我暂时无法回答这个问题，请尝试其他问题。' });
    await nextTick();
    scrollToBottom();
    return;
  }

  let aiReply = '';
  // 确保 text 是字符串类型
  const textToType = String(text);

  messages.value.push({ type: 'ai', text: '' }); // 先添加一个空AI消息占位
  const currentAiMessageIndex = messages.value.length - 1;

  for (let i = 0; i < textToType.length; i++) {
    aiReply += textToType[i];
    messages.value[currentAiMessageIndex].text = aiReply;
    await sleep(30); // 每个字之间的延迟
    await nextTick(); // 确保DOM更新
    scrollToBottom(); // 打字过程中也滚动到底部
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
  /* 修改背景颜色和透明度以与其他模块统一 */
  background-color: rgba(0, 0, 0, 0.5); /* */
  /* 保留原有的其他样式，并根据需要调整 */
  padding: 15px; /* 统一主要内边距，可以参考 .module 的 padding: 30px，或根据AI助手布局调整 */
  margin: 5px;   /* 这个可以保留，或者如果希望与其他模块的间距一致，可能需要外部容器控制 */
  border-radius: 12px; /* 与 .module 的 border-radius 一致 */ /* */
  box-shadow: 0 8px 16px rgba(0,0,0,0.3); /* 与 .module 的 box-shadow 一致 */ /* */
  flex: 1 1 calc(90% - 20px); /* 这些 flex 和 max-width 属性可能需要根据AI助手在布局中的实际角色来调整 */
  max-width: calc(95% - 20px);
  display: flex;
  flex-direction: column;
  height: 95%;
  transition: transform 0.2s ease-in-out; /* 保留原有的悬停过渡效果 */
  box-sizing: border-box;
}

/* 根据 .module:hover 的效果调整，如果需要的话 */
/* .chat-window:hover {
  transform: scale(1.03);
  box-shadow: 0 12px 24px rgba(0,0,0,0.4);
} */
/* 注意：原有的 handleMouseOver/Leave 已经是 scale(1.05)，这里仅为与 .module 对比 */


.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 0 10px 0; /* 调整header内边距，移除左右padding，因为父级已有 */
  margin-bottom: 10px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1); /* 示例：添加一个底部分隔线 */
  flex-shrink: 0;
}

.chat-box {
  flex-grow: 1;
  overflow-y: auto;
  padding-right: 5px; /* 为滚动条留出空间 */
  min-height: 100px;
}

.chat-message {
  margin: 10px 0;
  display: flex;
}

.user-message {
  background-color: hsla(160, 100%, 37%, 0.3); /* 使用主题绿色作为用户消息背景，调整透明度 */ /* */
  color: #f0f0f0; /* 用户消息文字颜色改为浅色以适应深色背景 */
  padding: 10px 12px;
  border-radius: 10px;
  max-width: 80%;
  margin-left: auto;
  text-align: left;
  word-break: break-word;
}

.ai-message {
  background-color: rgba(255, 255, 255, 0.15); /* AI消息背景使用更浅的半透明背景 */
  color: #e0e0e0; /* AI消息文字颜色 */
  padding: 10px 12px;
  border-radius: 10px;
  max-width: 80%;
  margin-right: auto;
  text-align: left;
  word-break: break-word;
}

.input-box {
  display: flex;
  gap: 10px;
  padding: 15px 0 0 0; /* 调整输入区域内边距，移除左右padding */
  margin-top: 10px;
  border-top: 1px solid rgba(255, 255, 255, 0.1); /* 示例：添加一个顶部分隔线 */
  flex-shrink: 0;
}

input {
  flex: 1;
  padding: 10px 12px;
  border-radius: 6px; /* 与 .custom-select 统一 */ /* */
  border: 1px solid rgba(255, 255, 255, 0.2); /* 边框颜色调整 */
  background-color: rgba(0, 0, 0, 0.3); /* 输入框背景调整 */
  color: #f0f0f0; /* 输入文字颜色 */
  font-size: 14px;
}
input::placeholder {
  color: #aaa;
}

button {
  padding: 10px 20px;
  background-color: hsla(160, 100%, 37%, 1); /* 按钮颜色使用主题绿色 */ /* */
  color: white;
  border: none;
  border-radius: 6px; /* 与 .custom-select 统一 */ /* */
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: hsla(160, 100%, 30%, 1); /* 主题绿色加深 */
}

button:disabled {
  background-color: #555;
  color: #aaa;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  padding: 8px 0;
  color: #ccc;
  font-size: 0.9em;
  flex-shrink: 0;
}
h2{ /* 继承自你提供的 .module 样式中的 h2 颜色 */
  color: white; /* */
  margin: 0; /* 重置可能的默认边距 */
  font-size: 1.1em; /* 调整字体大小 */
}

/* 自定义滚动条样式 (可选, WebKit 浏览器) */
.chat-box::-webkit-scrollbar {
  width: 6px;
}
.chat-box::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 3px;
}
.chat-box::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 3px;
}
.chat-box::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.4);
}
</style>