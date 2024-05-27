let express = require('express');
let router = express.Router();

let DARData = require('../mock/debtToAssertRatio.json')

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取资产负债率",
        DARData:DARData
    })
    //send内使用的是键值对的方式，所以data的值需要是一个对象
})

module.exports = router;