let express = require('express');
let router = express.Router();


let PERData = require('../mock/priceEarningsRatio.json');
let PBRData = require('../mock/priceBookRatio.json');
let PSRData = require('../mock/priceSalesRatio.json');
let DYData = require('../mock/dividendYield.json');

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取市盈率、市净率、市销率、 股息率",
        PERData:PERData,
        PBRData:PBRData,
        PSRData:PSRData,
        DYData:DYData
    })
})

module.exports = router;