(function(e){function t(t){for(var r,o,l=t[0],s=t[1],u=t[2],d=0,m=[];d<l.length;d++)o=l[d],Object.prototype.hasOwnProperty.call(i,o)&&i[o]&&m.push(i[o][0]),i[o]=0;for(r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r]);c&&c(t);while(m.length)m.shift()();return n.push.apply(n,u||[]),a()}function a(){for(var e,t=0;t<n.length;t++){for(var a=n[t],r=!0,l=1;l<a.length;l++){var s=a[l];0!==i[s]&&(r=!1)}r&&(n.splice(t--,1),e=o(o.s=a[0]))}return e}var r={},i={app:0},n=[];function o(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,o),a.l=!0,a.exports}o.m=e,o.c=r,o.d=function(e,t,a){o.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,t){if(1&t&&(e=o(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(o.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)o.d(a,r,function(t){return e[t]}.bind(null,r));return a},o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,"a",t),t},o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},o.p="/";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],s=l.push.bind(l);l.push=t,l=l.slice();for(var u=0;u<l.length;u++)t(l[u]);var c=s;n.push([0,"chunk-vendors"]),a()})({0:function(e,t,a){e.exports=a("56d7")},"034f":function(e,t,a){"use strict";var r=a("85ec"),i=a.n(r);i.a},"56d7":function(e,t,a){"use strict";a.r(t);a("e260"),a("e6cf"),a("cca6"),a("a79d");var r=a("2b0e"),i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("HelloWorld")],1)},n=[],o=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"hello"},[a("h1",[e._v("Hello, "+e._s(e.info.admin))]),a("div",[a("el-divider",{attrs:{"content-position":"left"}},[a("h2",[e._v("Insert Movie")])]),a("el-form",{attrs:{"label-width":"120px"}},[a("el-form-item",{attrs:{label:"Movie Title",required:""}},[a("el-input",{model:{value:e.movie.title,callback:function(t){e.$set(e.movie,"title",t)},expression:"movie.title"}})],1),a("el-form-item",{attrs:{label:"Movie Year",required:""}},[a("el-input",{model:{value:e.movie.year,callback:function(t){e.$set(e.movie,"year",t)},expression:"movie.year"}})],1),a("el-form-item",{attrs:{label:"Movie Director",required:""}},[a("el-input",{model:{value:e.movie.director,callback:function(t){e.$set(e.movie,"director",t)},expression:"movie.director"}})],1),a("el-form-item",{attrs:{label:"Movie Star",required:""}},[a("el-input",{model:{value:e.movie.star,callback:function(t){e.$set(e.movie,"star",t)},expression:"movie.star"}})],1),a("el-form-item",{attrs:{label:"Movie Genre",required:""}},[a("el-input",{model:{value:e.movie.genre,callback:function(t){e.$set(e.movie,"genre",t)},expression:"movie.genre"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitMovieForm()}}},[e._v("Submit")])],1)],1)],1),a("div",[a("el-divider",{attrs:{"content-position":"left"}},[a("h2",[e._v("Insert Star")])]),a("el-form",{attrs:{"label-width":"120px"}},[a("el-form-item",{attrs:{label:"Star Name",required:""}},[a("el-input",{model:{value:e.star.name,callback:function(t){e.$set(e.star,"name",t)},expression:"star.name"}})],1),a("el-form-item",{attrs:{label:"Star Birthday"}},[a("el-input",{model:{value:e.star.birth,callback:function(t){e.$set(e.star,"birth",t)},expression:"star.birth"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitStarForm()}}},[e._v("Submit")])],1)],1)],1),a("div",[a("el-divider",{attrs:{"content-position":"left"}},[a("h2",[e._v("DB INFO")])]),e._l(e.info.tables,(function(t,r){return a("div",{key:r},[a("h2",[e._v(e._s(t.name)+": ")]),a("el-table",{staticStyle:{width:"100%","margin-top":"20px"},attrs:{data:t.attr}},[a("el-table-column",{attrs:{prop:"name",label:"Field"}}),a("el-table-column",{attrs:{prop:"type",label:"Type"}})],1)],1)}))],2),a("el-button",{attrs:{type:"danger"},on:{click:function(t){return e.logout()}}},[e._v("Logout")])],1)},l=[],s=(a("b0c0"),{name:"HelloWorld",data:function(){return{info:{},star:{name:"",birth:""},movie:{title:"",year:"",director:"",star:"",genre:""}}},mounted:function(){var e=this;this.axios.get("/api/dash/show").then((function(t){0==t.data.message?e.info=t.data.data:-1==t.data.message?alert("auth error!"):alert(t.data.data)}))},methods:{submitMovieForm:function(){var e=this;this.axios.post("/api/dash/addMovie",null,{params:{title:this.movie.title,year:this.movie.year,director:this.movie.director,star:this.movie.star,genre:this.movie.genre}}).then((function(t){0==t.data.message?(alert(t.data.data),e.movie.title="",e.movie.year="",e.movie.director="",e.movie.star="",e.movie.genre=""):-1==t.data.message?alert("Auth Fail "+t.data.data):alert(t.data.data)}))},submitStarForm:function(){var e=this;this.axios.post("/api/dash/addStar",null,{params:{name:this.star.name,birth:this.star.birth}}).then((function(t){0==t.data.message?(alert(t.data.data),e.star.name="",e.star.birth=""):-1==t.data.message?alert("Auth Fail "+t.data.data):alert(t.data.data)}))},logout:function(){this.axios.get("/api/logout").then((function(e){-1==e.data.message?window.location.href="/login":alert("Logout Failed!")}))}}}),u=s,c=(a("92e7"),a("2877")),d=Object(c["a"])(u,o,l,!1,null,"140f1692",null),m=d.exports,f={name:"App",components:{HelloWorld:m}},p=f,v=(a("034f"),Object(c["a"])(p,i,n,!1,null,null,null)),b=v.exports,h=a("5c96"),g=a.n(h),y=(a("0fae"),a("bc3a")),x=a.n(y),_=a("a7fe"),w=a.n(_);r["default"].use(w.a,x.a),r["default"].config.productionTip=!1,r["default"].use(g.a),new r["default"]({render:function(e){return e(b)}}).$mount("#app")},"7b30":function(e,t,a){},"85ec":function(e,t,a){},"92e7":function(e,t,a){"use strict";var r=a("7b30"),i=a.n(r);i.a}});
//# sourceMappingURL=app.2b4e45d5.js.map