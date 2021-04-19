import config from './config'

export default ( url, data={}, method='GET' ) => {
  //consloe.log("传入url地址" + config.host);
  return new Promise((resolve, reject) => {
      wx.request({
        url: config.host + url,
        data,
        method,
        success(res){
          // consloe.log("请求成功"+res);
          resolve(res.data);
        },
        fail(err){
          reject(err);
        }
      })
  })
}