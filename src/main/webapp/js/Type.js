"use strict";
 
var Type = React.createClass({
	rawMarkup: function() {
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},
	handleClick: function(e) {
	    this.props.onTypeSelect(this.props.type.id);
	},
	render: function() {
		return (
				<div className="type">
					<h2 className="typeName" onClick={this.handleClick}>
						{this.props.type.name}
					</h2>
				</div>
		);
	}
});

var TypeList = React.createClass({
	render: function() {
	    var typeNodes = this.props.types.map(function(type) {
	      return (
	        <Type key={type.id} type={type} onTypeSelect={this.props.onTypeSelect}>
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
	    this.props.onTypeSubmit({name: name});
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

var TypeBox = React.createClass({
	loadTypesFromServer: function() {
		gapi.client.guidentifierApi.guidentifierApi.getTypes().execute(
			function(types) { 
				console.log('got data: ' + JSON.stringify(types)); 
				this.setState({types: types.items})
			}.bind(this)
		);
	},
	loadFamiliesFromServer: function(typeId) {
		if (typeId != null) {
			gapi.client.guidentifierApi.guidentifierApi.getFamilies({typeId: typeId}).execute(
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
			   function(response) {
				   console.log('added type: ' + JSON.stringify(response));
				   this.loadTypesFromServer();
			   }.bind(this)
	   );
	},
	handleFamilySubmit: function(typeId, familyName) {
		console.log(' adding family: ' + familyName);
	    gapi.client.guidentifierApi.guidentifierApi.addFamily({typeId: typeId, familyName: familyName}).execute(
			   function(response) {
				   console.log('added family: ' + JSON.stringify(response));
				   this.loadFamiliesFromServer(this.state.typeId);
			   }.bind(this)
	   );
	},
	handleTypeSelect: function(typeId) {
	    this.setState({typeId: typeId});
	    this.loadFamiliesFromServer(typeId);
	},	
	getInitialState: function() {
		return {types: [], typeId: null, families: []};
	},
	componentDidMount: function() {
	    this.loadTypesFromServer();
	},
	render: function() {
	    return (
	      <div className="typeBox">
	        <h1>Types</h1>
	        <TypeList types={this.state.types} onTypeSelect={this.handleTypeSelect}/>
	        <TypeForm onTypeSubmit={this.handleTypeSubmit} />
	        <FamilyBox typeId={this.state.typeId} families={this.state.families} onFamilySubmit={this.handleFamilySubmit}/>
	        </div>
	    );
	}
});

// add it to global context so we can reference this from javascript.
window.TypeBox = TypeBox;

