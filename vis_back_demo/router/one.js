let express = require('express');
let router = express.Router();

let oneData = require('../mock/one.json')
let testData = require('../mock/test1.json')

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"one.js的路由地址",
        chartData:oneData,
        test:testData
    })
    //send内使用的是键值对的方式，所以data的值需要是一个对象
})

module.exports = router;