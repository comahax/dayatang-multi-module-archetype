/**
 * 依赖jquery：uploadify插件
 * 封装上传控件，弹出上传窗口
 * Created with IntelliJ IDEA.
 * User: tune
 * Date: 13-6-20
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */

;(function($){
    var uploadifyOptions = {
        'auto'     : false,
        'buttonText': '选择文件',
        'buttonImage': null,
        //在action中的File类型变量
        'fileObjName': 'uploads',
        'swf'      : 'scripts/uploadify/uploadify.swf',
        'uploader' : '',
        'formData':{},
        'onUploadStart' : function(file) {
        },
        'onQueueComplete' : function(queueData) {
            imessager.tipCall(queueData+'上传完成',function(){
                //关闭上传dialog
            });
        },
        'onUploadComplete' : function(file){
        },
        'onCancel' : function(file){
            imessager.tipCall('文件【&nbsp;&nbsp;'+file.name+'&nbsp;&nbsp;】<br>取消上传',function(){
            });
        },
        'onUploadSuccess':function(file, data, response){
            imessager.tipCall(file.name+'上传成功');
        },
        'onUploadError':function(file, errorCode, errorMsg, errorString){
            if(errorCode != -280){
                imessager.tip(file.name+'上传失败'+',错误代码:&nbsp;'+errorCode);
            }
        }
    };

    var uploadify = {
        active:function(options){
            return this.each(function () {
                var $this = $(this);
                $this.uploadify(options);
            });
        },
        destory:function(){
            return this.each(function () {
                var $this = $(this);
                $this.uploadify('destroy');
            });
        }
    };

    var methods = {
        openDialog : function(target,formData,uploader,options){
            return this.each(function() {
                var $this = $(this);
                //激活上传控件
                uploadifyOptions.formData = formData;
                uploadifyOptions.uploader = uploader;
                uploadifyOptions.onQueueComplete = function(queueData){
                    imessager.tipCall(queueData+'上传完成',function(){
                        //关闭上传dialog
                        $this.dialog('close');
                    });
                }
                uploadifyOptions= $.extend({},uploadifyOptions,options);

                $this.dialog({
                    onBeforeOpen:function(){
                        uploadify['active'].call($(target),uploadifyOptions);
                    },
                    onBeforeClose:function(){
                        uploadify['destory'].call($(target));
                    }
                }).dialog('open');
            });
        }
    };
    /**
     * 用法$('dialogId').upload();
     * @param options
     * @returns {*|HTMLElement}
     */
        //打开窗口
    $.fn.dupload = function(options){
        var $this = $(this);
        var opts = $.extend({}, $.fn.dupload.defaults, options);
        if($(opts.uploadTarget).length >= 1){
            //打开窗口
            methods['openDialog'].call($this,opts.uploadTarget,opts.params,opts.url);
        }else{
           alert('不存在上传对象，或不存在上传地址');
        }
        return $this;
    };

    $.fn.uploadify.options = {
    }

    $.fn.dupload.defaults = {
        uploadTarget:null,
        url:'',
        params:{}
    }

})(jQuery);
