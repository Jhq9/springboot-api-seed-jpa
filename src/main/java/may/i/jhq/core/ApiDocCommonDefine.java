package may.i.jhq.core;

/**
 * @author jinhuaquan
 * @create 2018-01-23 下午5:56
 * @desc The common define of api define
 **/
public class ApiDocCommonDefine {

    /**
     * @apiDefine PageResponseParam
     * @apiSuccess {Object} data 分页相关数据
     * @apiSuccess {Boolean} data.first 是否是第一页
     * @apiSuccess {Boolean} data.last 是否是最后一页
     * @apiSuccess {Number} data.number 数据库内次序
     * @apiSuccess {Number} data.numberOfElements 显示次序
     * @apiSuccess {Number} data.size 页的大小
     * @apiSuccess {Object} data.sort 排列规则
     * @apiSuccess {Boolean} data.sort.ascending 是否升序
     * @apiSuccess {Boolean} data.sort.descending 是否降序
     * @apiSuccess {String} data.sort.direction 排列方向:ASC表示升序，DESC表示降序
     * @apiSuccess {Boolean} data.sort.ignoreCase 是否忽略大小写
     * @apiSuccess {String} data.sort.nullHandling 如何处理的null值
     * @apiSuccess {String} data.sort.property 排序的字段
     * @apiSuccess {Number} data.totalElements 总条数
     * @apiSuccess {Number} data.totalPages 总页数
     * @apiSuccess {Object[]} data.content 分页获取的数据内容
     */

    /**
     * @apiDefine JsonHeader
     * @apiHeader {String} Authorization 用户的token
     * @apiHeader {String} Content-Type="application/json;charset=UTF-8" Json类型的请求
     */

    /**
     * @apiDefine FormHeader
     * @apiHeader {String} Authorization 用户的token
     * @apiHeader {String} Content-Type="multipart/form-data" 表单数据类型的请求
     */

    /**
     * @apiDefine NormalResponse
     *
     * @apiSuccess {Number} code="200" 状态码，请求正确返回200.
     * @apiSuccess {String} message="Success" 请求结果信息，请求正确返回"Success".
     *
     * @apiError {Number} code 状态码，请求存在错误返回400，权限不足返回401，其他非正常错误返回500.
     * @apiError {String} message 错误描述信息。
     */

    /**
     * @apiDefine JsonIdResponse
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "code":200,
     *      "message":"Success",
     *      "data":1
     * }
     */

    /**
     * @apiDefine JsonBoolResponse
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "code":200,
     *      "message":"Success",
     *      "data":true
     * }
     */

    /**
     * @apiDefine JsonPageResponse
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *      "code": 200,
     *      "data": {
     *          "content": [],
     *          "first": true,
     *          "last": true,
     *          "number": 0,
     *          "numberOfElements": 1,
     *          "size": 10,
     *          "sort": [{
     *              "ascending": false,
     *              "descending": true,
     *              "direction": "DESC",
     *              "ignoreCase": false,
     *              "nullHandling": "NATIVE",
     *              "property": "id"
     *            }],
     *          "totalElements": 1,
     *          "totalPages": 1
     *          },
     *      "message": "SUCCESS"
     *      }
     */
}
