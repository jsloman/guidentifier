// In webpack.config.js
var HtmlWebpackPlugin = require('html-webpack-plugin')
var HTMLWebpackPluginConfig = new HtmlWebpackPlugin({
  template: __dirname + '/src/main/webapp/app.html',
  filename: 'app.html',
  inject: 'body'
});

module.exports = {
    entry: [
        './src/main/webapp/scripts/index.js'
    ],
    module: {
        loaders: [
            { test: /\.js[x]?$/, exclude: /node_modules/, loaders: ['babel-loader'] },
        ]
    },
    output: {
        filename: "index_bundle.js",
        path: __dirname + '/dist'
    },
    plugins: [HTMLWebpackPluginConfig]		
};