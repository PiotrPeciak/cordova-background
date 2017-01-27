# cordova-background #

My cordova test plugin for execute task/jobs in background

### Using ###

It has two ways for trying to schedule background tasks from JS:
by 'AlarmManager':
```JavaScript
cordova.plugins.testService.alarm();
```
and by 'JobScheduler':
```JavaScript
cordova.plugins.testService.job();
```

Currently plugin is in test phase, just for looking the best way to schedule stable cycle jobs :)