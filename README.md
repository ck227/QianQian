# QianQian
1.设置本地git邮箱 git config --global user.email 你的邮件地址

2. Caused by: android.content.res.Resources$NotFoundException: Resource ID #0x7f070058

Seems like the issue is related to Aapt 2.
Put
android.enableAapt2=false
In your gradle.properties file and it fixes the issue for me.

3.


