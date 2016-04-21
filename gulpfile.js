var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var react = require('gulp-react');
var htmlreplace = require('gulp-html-replace');

var path = {
		  HTML: 'src/main/webapp/app.html',
		  ALL: ['src/main/webapp/js/*.js', 'src/main/webapp/js/**/*.js', 'src/main/webapp/app.html'],
		  JS: ['src/main/webapp/js/*.js', 'src/main/webapp/js/**/*.js'],
		  MINIFIED_OUT: 'build.min.js',
		  DEST_SRC: 'build/exploded-app',
		  DEST_BUILD: 'dist/build',
		  DEST: 'dist'
		};

gulp.task('transform', function(){
	  gulp.src(path.JS)
	    .pipe(react())
	    .pipe(gulp.dest(path.DEST_SRC));
	});

gulp.task('copy', function(){
	  gulp.src(path.HTML)
	    .pipe(gulp.dest(path.DEST));
	});

gulp.task('watch', function(){
	  gulp.watch(path.ALL, ['transform', 'copy']);
	});

gulp.task('default', ['watch']);

gulp.task('replaceHTML', function(){
	  gulp.src(path.HTML)
	    .pipe(htmlreplace({
	      'js': 'build/' + path.MINIFIED_OUT
	    }))
	    .pipe(gulp.dest(path.DEST));
	});

gulp.task('production', ['replaceHTML', 'build']);

