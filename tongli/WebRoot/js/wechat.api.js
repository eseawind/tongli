function wechatAPI() {
    var shareData = {
	shareImageUrl: "",
	shareLink: "",
	shareTitle: "",
	shareContent: "",
    }
    var network;
    /*****分享*****/
    this.shareInit = function() {
	var shareData = this.shareData;
	window.shareData = {
	    shareImageUrl: shareData['shareImageUrl'],
	    shareLink: shareData['shareLink'],
	    shareTitle: shareData['shareTitle'],
	    shareContent: shareData['shareContent'],
	};
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 发送给好友
	    WeixinJSBridge.on('menu:share:appmessage', function(argv) {
		WeixinJSBridge.invoke('sendAppMessage', {
		    "img_url": shareData['shareImageUrl'],
		    "img_width": "640",
		    "img_height": "640",
		    "link": shareData['shareLink'],
		    "desc": shareData['shareContent'],
		    "title": shareData['shareTitle']
		}, function(res) {
		    _report('send_msg', res.err_msg);
		})
	    });

	    // 分享到朋友圈
	    WeixinJSBridge.on('menu:share:timeline', function(argv) {
		WeixinJSBridge.invoke('shareTimeline', {
		    "img_url": shareData['shareImageUrl'],
		    "img_width": "640",
		    "img_height": "640",
		    "link": shareData['shareLink'],
		    "desc": shareData['shareContent'],
		    "title": shareData['shareContent']
		}, function(res) {
		    _report('timeline', res.err_msg);
		});
	    });

	    // 分享到微博
	    WeixinJSBridge.on('menu:share:weibo', function(argv) {
		WeixinJSBridge.invoke('shareWeibo', {
		    "img_url": shareData['shareImageUrl'],
		    "content": shareData['shareContent'],
		    "url": shareData['shareLink'],
		    "link": shareData['shareLink'],
		}, function(res) {
		    _report('weibo', res.err_msg);
		});
	    });
	});
    }
    /*****隐藏底部工具条*****/
    this.hideBottomTool = function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    WeixinJSBridge.call('hideToolbar');
	});
    }
    /*****隐藏右上角工具条*****/
    this.hideTopTool = function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    WeixinJSBridge.call('hideOptionMenu');
	});
    }
    /*****获取网络状态*****/
    this.getNetwork = function() {
	var network = this.network;
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    WeixinJSBridge.invoke('getNetworkType', {},
		    function(e) {
			//WeixinJSBridge.log(e.err_msg);
			var str = e.err_msg;
			strs = str.split(":"); //字符分割
			var network = strs[1];
		    });
	});
    }
}