var data = [
  {id: 1, author: "Pete Hunt", text: "This is one comment"},
  {id: 2, author: "Jordan Walke", text: "This is *another* comment"}
];

var Comment = React.createClass({
  rawMarkup: function() {
    var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
    return { __html: rawMarkup };
  },

  render: function() {
    return (
      <div className="comment">
        <h2 className="commentAuthor">
          {this.props.author}
        </h2>
        <span dangerouslySetInnerHTML={this.rawMarkup()} />
      </div>
    );
  }
});

var CommentList = React.createClass({
	  render: function() {
	    var commentNodes = this.props.data.map(function(comment) {
	      return (
	        <Comment author={comment.id} key={comment.id}>
	          {comment.name}
	        </Comment>
	      );
	    });
	    return (
	      <div className="commentList">
	        {commentNodes}
	      </div>
	    );
	  }
	});

var CommentForm = React.createClass({
  render: function() {
    return (
      <div className="commentForm">
        Hello, world! I am a CommentForm.
      </div>
    );
  }
});


var CommentBox = React.createClass({
	  getInitialState: function() {
	    return {data: []};
	  },
	  componentDidMount: function() {
			gapi.client.guidentifierApi.guidentifierApi.getTypes().execute(
					function(mydata){ 
						console.log('got data: ' + JSON.stringify(mydata)); 
						this.setState({data: mydata.items})
					}.bind(this)
			);
		  },
	  render: function() {
	    return (
	      <div className="commentBox">
	        <h1>Comments</h1>
	        <CommentList data={this.state.data} />
	        <CommentForm />
	      </div>
	    );
	  }
	});

// add it to global context so we can reference this from javascript.
window.CommentBox = CommentBox;

