import React, {Component} from 'react';
import {
  requireNativeComponent,
  View
} from 'react-native';
import PropTypes from 'prop-types';


let RCTGIFImageView = requireNativeComponent('ReactImageManager', RCTGIFView,{nativeOnly:{onClick:true}});


export default class RCTGIFView extends Component {
	
	 static propTypes = {
        imageName: PropTypes.string,     
        onClick: PropTypes.func,
        ...View.propTypes // 包含默认的View的属性
    };
    
	constructor(props) {
        super(props);
    }
	  /**
   * 单击事件
   */
  onClick(event) {
  	console.log("fnegxing 点击了"+event.nativeEvent.msg);
    if(this.props.onClick) {
      if(!this.props.onClick){
        return;
      }
      console.log("fnegxing1 点击了"+event.nativeEvent.msg);
      // 使用event.nativeEvent.msg获取原生层传递的数据
      this.props.onClick({msg:event.nativeEvent.msg});
    }
}
  
  render() {
   return <RCTGIFImageView  
              {...this.props}
              onClick={ (event)=> this.onClick(event) }
               />
  }
}
 
