var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin')

var HTMLWebpackPluginConfig = new HtmlWebpackPlugin({
  template: __dirname + '/app.html',
  inject: 'head'
});

module.exports = {
    entry: {
        category: "./jsx/main.js"
    },
    module: {
        loaders: [
            { test: /\.js[x]?$/, exclude: /node_modules/, loaders: ['babel-loader'] },
        ]
    },
    output: {
    	path: path.join(__dirname, 'dist'),
    	filename: 'bundle.js',
    	publicPath: '/'
    },
    plugins: [new webpack.HotModuleReplacementPlugin(), HTMLWebpackPluginConfig]		
};