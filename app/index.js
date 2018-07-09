/**
 * Created by marno on 2017/2/4
 * Function:为了兼容Android和iOS设置统一程序入口
 * Desc:
 */
import React, {Component} from "react";
import {AppRegistry} from "react-native";
import './utils/StorageUtil';

 import HelloWorld from "./00_helloworld_demo/HelloWorld";
 import FlexTest from "./01_flex_demo/FlexTest";
 import FlexDiceTest from "./01_flex_demo/FlexDiceTest";
 import FetchNetData from "./02_fetch_demo/FetchNetData";
 import BannerTest from './03_library_demo/BannerTest';
 import TopTabViewTest from './03_library_demo/tab_demo/TopTabViewTest';
 import BottomTabViewTest from './03_library_demo/tab_demo/BottomTabViewTest'
 import TaberTest from './03_library_demo/tab_demo/TaberTest'
// import AnatomyTest from './04_nativebase_demo/AnatomyTest';
 import ScrollViewTest from './05_scroll_demo/ScrollViewTest'
 import ListViewTest from './05_scroll_demo/ListViewTest';
 import MainPage from './eyepetizer_demo/MainPage';
//import StartUp from './eyepetizer_demo/StartUp';
 //import ParallaxTest from './03_library_demo/ParallaxTest';

 AppRegistry.registerComponent('HelloWorld', () => HelloWorld);
 AppRegistry.registerComponent('HelloWorld', ()=>FlexTest);
 AppRegistry.registerComponent('HelloWorld', () => FlexDiceTest);
 AppRegistry.registerComponent('HelloWorld', () => FetchNetData);
 AppRegistry.registerComponent('HelloWorld', () => BannerTest);
 AppRegistry.registerComponent('HelloWorld', ()=>TopTabViewTest);
 AppRegistry.registerComponent('HelloWorld', ()=>BottomTabViewTest);
 AppRegistry.registerComponent('HelloWorld', ()=>TaberTest);
// AppRegistry.registerComponent('HelloWorld', ()=>AnatomyTest);
 AppRegistry.registerComponent('HelloWorld', ()=>ScrollViewTest);
 AppRegistry.registerComponent('HelloWorld', ()=>ListViewTest);
 AppRegistry.registerComponent('HelloWorld', ()=>MainPage);
 //AppRegistry.registerComponent('HelloWorld', ()=>ParallaxTest);