var Type = React.createClass({
	rawMarkup: function() {
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},

	render: function() {
		return (
				<div className="type">
					<h2 className="typeName">
						{this.props.name}
					</h2>
				</div>
		);
	}
});

var TypeList = React.createClass({
	render: function() {
	    var typeNodes = this.props.data.map(function(type) {
	      return (
	        <Type key={type.id} name={type.name}>
	          {type.name}
	        </Type>
	      );
	    });
	    return (
	      <div className="typeList">
	        {typeNodes}
	      </div>
	    );
	}
});

var TypeForm = React.createClass({
	getInitialState: function() {
	  return {name: ''};
	},
	handleNameChange: function(e) {
	  this.setState({name: e.target.value});
	},
	handleSubmit: function(e) {
		e.preventDefault();
		var name = this.state.name.trim();
		if (!name) {
			return;
		}
	    this.props.onCommentSubmit({name: name});
		this.setState({name: ''});
	},
	render: function() {
		return (
	      <form className="commentForm" onSubmit={this.handleSubmit}>
	        <input
	          type="text"
	          placeholder="Type name"
	          value={this.state.name}
	          onChange={this.handleNameChange}
	        />
	        <input type="submit" value="Post" />
	      </form>
	    );
	}
});


var TypeBox = React.createClass({
	loadCommentsFromServer: function() {
		gapi.client.guidentifierApi.guidentifierApi.getTypes().execute(
			function(mydata) { 
				console.log('got data: ' + JSON.stringify(mydata)); 
				this.setState({data: mydata.items})
			}.bind(this)
		);
	},
	handleTypeSubmit: function(typeObj) {
		console.log(' adding type: ' + typeObj + ' which is: ' + JSON.stringify(typeObj));
	    gapi.client.guidentifierApi.guidentifierApi.addType(typeObj).execute(
			   function(mydata) {
				   console.log('added type: ' + JSON.stringify(mydata));
				   this.loadCommentsFromServer();
			   }.bind(this)
	   );
	},
	getInitialState: function() {
		return {data: []};
	},
	componentDidMount: function() {
	    this.loadCommentsFromServer();
	},
	render: function() {
	    return (
	      <div className="typeBox">
	        <h1>Types</h1>
	        <TypeList data={this.state.data} />
	        <TypeForm onCommentSubmit={this.handleTypeSubmit} />
	      </div>
	    );
	}
});

// add it to global context so we can reference this from javascript.
window.TypeBox = TypeBox;

