let express = require('express');
let app = express();

//设置跨域
app.use(function(req,res,next){
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Content-Type,Content-Length,Authorization, Accept, X-Requested-With , yourHeaderFeild');
    res.header('Access-Control-Allow-Methods', 'PUT, POST, GET,DELETE,OPTIONS');
    // 千万不要网%%￥￥￥###
    // 千万不要网
    // 千万不要网
    next();
    })

//引用路由文件
let chartOne = require('./router/one');
let chartTwo = require('./router/two');
let chartThree = require('./router/three');
let chartFour = require('./router/four');
let chartFive = require('./router/five');
let CRandQR = require('./router/CRandQRRouter');
let DAR = require('./router/DARRouter');
let mid = require('./router/mid');

//使用中间件来配置路由
app.use('/one', chartOne);
app.use('/two', chartTwo);
app.use('/three', chartThree);
app.use('/four', chartFour);
app.use('/five', chartFive);
app.use('/CRandQRRouter', CRandQR);
app.use('/DARRouter', DAR);
app.use('/mid', mid);

//监听端口
app.listen(8888)