var Type = React.createClass({
	rawMarkup: function() {
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},
	handleClick: function(e) {
		console.log('Got handleClick: ' + this.props.id);
	    this.props.onTypeSelect(this.props.id);
	},
	render: function() {
		return (
				<div className="type">
					<h2 className="typeName" onClick={this.handleClick}>
						{this.props.name}
					</h2>
				</div>
		);
	}
});

var TypeList = React.createClass({
	render: function() {
	    var typeNodes = this.props.types.map(function(type) {
	      return (
	        <Type key={type.id} id={type.id} name={type.name} onTypeSelect={this.props.onTypeSelect}>
	          {type.name}
	        </Type>
	      );
	    }.bind(this));
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
	        <input type="submit" value="Add type" />
	      </form>
	    );
	}
});

var GuideBox = React.createClass({
	render: function() {
	    var familyNodes = this.props.families.map(function(family) {
	      return (
	    		<div className="family">
					{family.name}
				</div>
	      );
	    });
	    return (
	      <div className="guideBox">
	        <h1>{this.props.type}</h1>
	        <div className="familyList">
	          {familyNodes}
	        </div>
	      </div>
	    );
	}
});

var TypeBox = React.createClass({
	loadTypesFromServer: function() {
		gapi.client.guidentifierApi.guidentifierApi.getTypes().execute(
			function(types) { 
				console.log('got data: ' + JSON.stringify(types)); 
				this.setState({types: types.items})
			}.bind(this)
		);
	},
	loadFamiliesFromServer: function() {
		if (this.state.type != null) {
			gapi.client.guidentifierApi.guidentifierApi.getFamilies(this.state.type).execute(
					function(families) { 
						console.log('got family data: ' + JSON.stringify(families)); 
						this.setState({families: families.items})
					}.bind(this)
				);		
		}	
	},
	handleTypeSubmit: function(typeObj) {
		console.log(' adding type: ' + typeObj + ' which is: ' + JSON.stringify(typeObj));
	    gapi.client.guidentifierApi.guidentifierApi.addType(typeObj).execute(
			   function(mydata) {
				   console.log('added type: ' + JSON.stringify(mydata));
				   this.loadTypesFromServer();
			   }.bind(this)
	   );
	},
	handleTypeSelect: function(type) {
	    this.setState({type: type});
	    this.loadFamiliesFromServer();
	},	
	getInitialState: function() {
		return {types: [], type: null, families: []};
	},
	componentDidMount: function() {
	    this.loadTypesFromServer();
	},
	render: function() {
	    return (
	      <div className="typeBox">
	        <h1>Types</h1>
	        <TypeList types={this.state.types} onTypeSelect={this.handleTypeSelect}/>
	        <TypeForm onCommentSubmit={this.handleTypeSubmit} />
	        <GuideBox type={this.state.type} families={this.state.families}/>
	        </div>
	    );
	}
});

// add it to global context so we can reference this from javascript.
window.TypeBox = TypeBox;

