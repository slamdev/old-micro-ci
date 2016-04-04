'use strict';

var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();

var src = './src/main';
var build = './build/public';

gulp.task('index', function () {
    var target = gulp.src(src + '/index.html');
    var order = ['jquery.js', '**'];
    var sources = gulp.src([build + '/**/*.js', build + '/**/*.css']).pipe(plugins.order(order));
    var injectOptions = {
        addRootSlash: false,
        ignorePath: 'build/public'
    };
    return target.pipe(plugins.inject(sources, injectOptions)).pipe(gulp.dest(build));
});

gulp.task('js', function () {
    var sources = gulp.src(['./node_modules/jquery/dist/jquery.js', src + '/**/*.js']);
    return sources.pipe(gulp.dest(build));
});

gulp.task('css', function () {
    var sources = gulp.src(src + '/**/*.css', {read: false});
    return sources.pipe(gulp.dest(build));
});

gulp.task('clean', function () {
    return gulp.src(build, {read: false}).pipe(plugins.clean());
});

gulp.task('default', ['clean', 'css', 'js'], function () {
    gulp.start('index')
});
