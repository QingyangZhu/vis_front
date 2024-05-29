let express = require('express');
let router = express.Router();

let NPGData = require('../mock/netProfitGrowthRate.json');
let SGRData = require('../mock/salesGrowthRate.json');
let TAGData = require('../mock/totalAssetGrowthRate.json');

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取销售增长率、 净利润增长率、总资产增长率",
        NPGData:NPGData,
        SGRData:SGRData,
        TAGData:TAGData
    })
})

module.exports = router;