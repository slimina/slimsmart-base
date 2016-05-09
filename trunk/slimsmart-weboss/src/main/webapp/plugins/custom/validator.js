//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉子
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //移动手机号码验证
    mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1\d{10}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不正确'
    },
    //移动手机号码前7位验证
    mobilePrefix: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1\d{6}$/;
            return reg.test(value);
        },
        message: '输入手机号码前7位'
    },
  //电话验证
    telphone: {//value值为文本框中的值
        validator: function (value) {
            var reg = /(^(\d{3,4}-?)?\d{7,8})$|(1\d{10})/;
            return reg.test(value);
        },
        message: '输入联系电话格式不正确'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字'
    },
    positiveNumber :{
    	validator: function (value) {
            return (parseFloat(value)>0);
        },
        message: '请输入一个正数'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();  
        },  
        message:'两次输入的不一致'  
    }
});