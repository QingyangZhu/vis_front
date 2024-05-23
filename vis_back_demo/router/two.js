let express = require('express');
let router = express.Router();

/* GET home page. */
router.get('/data', (req,res)=> {
    res.send({msg:"one.js的路由地址"})
})

module.exports = router;