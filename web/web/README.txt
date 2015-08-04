dash.html是登录以后跳转到的页面，最后应该改成php，因为有一些内容需要动态生成。
第28行，做一个登出logout.php，清除session信息。
第56行，“XX（用PHP修改）”改成php查询出来的社团名
第176行，$.get(...)是用来获取社团信息列表JSON的PHP，改成真正PHP文件名字，社团ID没有给，在PHP端用SESSION查找（PHP可以考虑如果给ID就按ID算，否则看session）
第179行，info_list = data.contents;检查一下对不对
把第233、246、258行的PHP文件名改成正确的名称。
