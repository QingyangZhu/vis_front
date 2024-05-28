let express = require('express');
let router = express.Router();

let TARData = require('../mock/totalAssertReturnRate.json');
let NARData = require('../mock/netAssertReturnRate.json');
let NPMData = require('../mock/netProfitMargin.json');

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取净利率、净资产收益率、总资产报酬率",
        TARData:TARData,
        NARData:NARData,
        NPMData:NPMData
    })
})

module.exports = router;