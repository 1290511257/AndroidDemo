var translate = require("google-translate-api");
var DOMParser = require("xmldom").DOMParser;
var fs = require("fs");
var path = require("path");
var filePath = path.resolve('../xmlReader/strings.xml');

// 默认string.xml文件字符串,保存处理
var xml_string_default;
var xml_string_map = new Map();

//bb2项目翻译语言及其对应文件夹
var bb2_language_map = new Map([
    ["am", "values-am-rET"],
    ["fr", "values-fr"],
    ["zh-cn", "values-zh-rCN"],
    ["zh-tw", "values-zh-rHK"],
]);

var test_language_map = new Map([
    ["zh-cn", "values-am-rET"]]);

//翻译目标语言及目标文件夹,对应项目选择需要翻译的map
var targetLanguage = test_language_map;

fileTranslate(filePath);




function fileTranslate(filePath) {

    var xml_string_default = fs.readFileSync(filePath, 'utf-8');
    if ("" != xml_string_default) {//读取默认string.xml文件成功
        var doc = new DOMParser().parseFromString(xml_string_default, 'text/xml');
        var stringDomArray = doc.getElementsByTagName("string");

        for (var i = 0; i < stringDomArray.length; i++) {//键值对存储需要翻译的字符串
            if ("false" !== stringDomArray[i].getAttribute("translatable")) {
                var string_name = stringDomArray[i].getAttribute("name");
                if (string_name !== "" && null !== string_name) {
                    var string_value = stringDomArray[i].textContent;
                    xml_string_map.set(string_name, string_value);
                } else {
                    console.log("------>" + stringDomArray[i].getAttribute("name") + " 无name属性,未翻译.")
                }
            } else {
                console.log("------>" + stringDomArray[i].getAttribute("name") + " 无需翻译.");
            }
        }
    }



    for (var [lang_name, lang_dir] of targetLanguage) {

        var write_file_string = xml_string_default;
        var trans_dir_path = "./" + lang_dir;
        var trans_file_path = "./" + lang_dir + "/string.xml";

        if (!fs.existsSync(trans_file_path)) {//无基础翻译文件
            if (!fs.existsSync(trans_dir_path)) {
                fs.mkdirSync(trans_dir_path);
            }
            var doc = new DOMParser().parseFromString(write_file_string, 'text/xml');
            var stringDomArray = doc.getElementsByTagName("string");
            var indexArray = new Array();
            var resultArray = new Array();
            for (var i = 0; i < stringDomArray.length; i++) {//键值对存储需要翻译的字符串
                if ("false" !== stringDomArray[i].getAttribute("translatable")) {
                    var string_name = stringDomArray[i].getAttribute("name");
                    if (string_name !== "" && null !== string_name) {//需要翻译的对象
                        var trans_string = stringDomArray[i].textContent.trim();
                        if (null !== trans_string && trans_string !== "") {
                            console.log(">>>>>" + trans_string + "--->" + lang_name);
                            indexArray.push(i);
                            translate(trans_string, { from: 'en', to: "zh-cn" }).then(res => {
                                console.log(resultArray.push(res.text) + ";" + res.text);
                            }).catch(err => {
                                console.error(err);
                            });

                            // while (block) {//阻塞线程
                            //     // console.log("block");
                            //     var now = new Date();//休眠
                            //     var exitTime = now.getTime() + 1000;
                            //     var b = true;
                            //     while (b) {
                            //         // console.log("sleep");
                            //         now = new Date();
                            //         if (now.getTime() > exitTime)
                            //             b = false;
                            //     }
                            // }

                        }
                    } else {
                        // console.log("------>" + stringDomArray[i].getAttribute("name") + " 无name属性,未翻译.")
                    }
                } else {
                    // console.log("------>" + stringDomArray[i].getAttribute("name") + " 无需翻译.");
                }
            }

            


        } else {
            fs.stat("./" + lang_dir, function (err, stat) {
                if (err) {
                    console.error(err);
                    return false;
                }

                stat
            })
            fs.writeFile(trans_file_path, "454545", "utf-8", function (err) {
                if (err) {
                    console.error(err);
                } else {

                }

            });

        }

        console.log(lang_name + ": " + lang_dir);
    }

    console.log(">>>>>>>>1111")

}

console.log(">>>>>>>222");
async function translateAsync(string_value, lang_opt) {
    return await translate(string_value, { from: 'en', to: lang_opt });
}