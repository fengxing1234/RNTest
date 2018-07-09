import React, {Component} from "react";
import {StyleSheet, Text, View,NativeModules,UIManager, findNodeHandle} from "react-native";
import Banner from "react-native-banner";
import RCTGIFView from '../eyepetizer_demo/RCTGIFView';
import WebView from '../eyepetizer_demo/WebView';

/**
 * Created by marno on 2017/1/13.
 */
export default class BannerTest extends Component {
 constructor(props) {
        super(props);
        this.state = {
        	 isPlaying:true,
        	 global: '这个是预定的接受信息',      
      	 image: 'content://media/external/images/media/21895',	
        }
    }

    render() {
        return (
            <View style={[styles.container]}>
                <Text style={{fontSize : 25}} onPress = {this.CallAndroid_promise}> 调用原生方法_使用_Promise</Text>
                
                 <Text style={styles.welcome} >
        				{this.state.global}
      			</Text>	
             	
             	 <RCTGIFView              	 
  					ref='RCTImageView'
    				    style={ styles.image }
    				    playStatus={ this.state.isPlaying }
          			imageName={ this.state.image }           			
 					onClick = { this.onClick }
 					/>
 					
 					<WebView
 					onScrollChange={this.onWebViewScroll}
                    url="https://www.baidu.com"
                    style={{width: 400, height: 200}}/>
            </View>
        );
    }
    
sendNotification() {
  //向native层发送命令
  UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.refs.RCTImageView),
      UIManager.RCTImageView.Commands.handleTask, // Commands后面的值与原生层定义的HANDLE_METHOD_NAME一致
      ['100','101'] // 向原生层传递的参数数据,数据形如：["第一个参数","第二个参数",3]
  )
 }


    onClick(event){
    	alert('原生层传递的数据为：', event)
    }
    
    onWebViewScroll(event) {
        console.log(event);
    }
    
    CallAndroid_promise = ()=>{
    		NativeModules.ToastShow.getResultForPromise("rn调用原生模块的方法-成功啦").
    		then(
    			(msg)=>{
    				this.setState({global:msg});
    				console.log('promise成功：'+msg);
    			}
    		).catch(
    			(error)=>{
    				console.log(err);
    			}
    		);
    }

    clickListener(index) {
        this.setState({
            clickTitle: this.banners[index].title ? `you click ${this.banners[index].title}` : 'this banner has no title',
        })
    }

    onMomentumScrollEnd(event, state) {
        console.log(`--->onMomentumScrollEnd page index:${state.index}, total:${state.total}`);
        this.defaultIndex = state.index;
    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F5FCFF',
    },
    image : {
     width:120,
     height:120,
    	 backgroundColor: '#000000',
    }
});