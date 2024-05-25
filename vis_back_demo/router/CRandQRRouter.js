let express = require('express');
let router = express.Router();

let CRData = require('../mock/currentRatio.json')
let QRData = require('../mock/quickRatio.json')

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取速动比率和流动比率",
        CRData:CRData,
        QRData:QRData
    })
    //send内使用的是键值对的方式，所以data的值需要是一个对象
})

module.exports = router;