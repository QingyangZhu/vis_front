let express = require('express');
let router = express.Router();

let ITData = require('../mock/inventoryTurnover.json')
let RTData = require('../mock/receivablesTurnover.json')
let TTData = require('../mock/totalAssertTurnover.json')

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({
        msg:"获取总资产周转率、存货周转率、应收账款周转率",
        TTData:TTData,
        ITData:ITData,
        RTData:RTData
    })
})

module.exports = router;