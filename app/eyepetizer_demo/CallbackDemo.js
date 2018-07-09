/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
 
import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  NativeModules,
  View,
  TouchableOpacity
} from 'react-native';
 
export default class CallbackDemo extends Component {
	constructor(props) {
    super(props);
    this.state = {
        global: '这个是预定的接受信息',
    }
}
  render() {
  
  	var Globle="null";
    return (
 
      <View style={styles.container}>
    
        <TouchableOpacity style={styles.button1}
        onPress={this.call_button_show.bind(this)}>
        <Text style={styles.welcome}
        >
        显示信息
        </Text>
        </TouchableOpacity>
 
      <Text style={styles.welcome} >
        {this.state.global}
      </Text>
     
     
      </View>
    );
  }
 
 
 call_button_show(){
    Globle="null";
 
 	NativeModules.ToastShow.getResult((result)=>{this.setState({global:result,});});
 }
 
 
}
 
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
    button1:{
    height: 40,
    width: 100,
    marginTop:1,
    backgroundColor:'gray',
    },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});