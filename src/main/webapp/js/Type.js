"use strict";
 
var Category = React.createClass({
	rawMarkup: function() {
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},
	handleClick: function(e) {
	    this.props.onCategorySelect(this.props.category.name);
	},
	render: function() {
		return (
				<div className="category">
					<h2 className="categoryName" onClick={this.handleClick}>
						{this.props.category.name}
					</h2>
				</div>
		);
	}
});

var CategoryList = React.createClass({
	render: function() {
	    var categoryNodes = this.props.categories.map(function(category) {
	      return (
	        <Category key={category.name} category={category} onCategorySelect={this.props.onCategorySelect}>
	          {category.name}
	        </Category>
	      );
	    }.bind(this));
	    return (
	      <div className="categoryList">
	        {categoryNodes}
	      </div>
	    );
	}
});

var CategoryForm = React.createClass({
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
	    this.props.onCategorySubmit({name: name});
		this.setState({name: ''});
	},
	render: function() {
		return (
	      <form className="commentForm" onSubmit={this.handleSubmit}>
	        <input
	          type="text"
	          placeholder="Category name"
	          value={this.state.name}
	          onChange={this.handleNameChange}
	        />
	        <input type="submit" value="Add category" />
	      </form>
	    );
	}
});

var CategoryBox = React.createClass({
	loadCategoriesFromServer: function() {
		gapi.client.guidentifierApi.guidentifierApi.getCategories().execute(
			function(categories) { 
				console.log('got data: ' + JSON.stringify(categories)); 
				this.setState({categories: categories.items})
			}.bind(this)
		);
	},
	loadGroupsFromServer: function(categoryName) {
		if (categoryName != null) {
			gapi.client.guidentifierApi.guidentifierApi.getGroups({categoryName: categoryName}).execute(
					function(groups) { 
						console.log('got group data: ' + JSON.stringify(groups)); 
						this.setState({groups: groups.items})
					}.bind(this)
				);		
		}	
	},
	handleCategorySubmit: function(categoryObj) {
		console.log(' adding category: ' + categoryObj + ' which is: ' + JSON.stringify(categoryObj));
	    gapi.client.guidentifierApi.guidentifierApi.addCategory(categoryObj).execute(
			   function(response) {
				   console.log('added category: ' + JSON.stringify(response));
				   this.loadCategoriesFromServer();
			   }.bind(this)
	   );
	},
	handleGroupSubmit: function(categoryName, groupName) {
		console.log(' adding group: ' + groupName);
	    gapi.client.guidentifierApi.guidentifierApi.addGroup({categoryName: categoryName, groupName: groupName}).execute(
			   function(response) {
				   console.log('added group: ' + JSON.stringify(response));
				   this.loadGroupsFromServer(this.state.categoryName);
			   }.bind(this)
	   );
	},
	handleCategorySelect: function(categoryName) {
	    this.setState({categoryName: categoryName});
	    this.loadGroupsFromServer(categoryName);
	},	
	getInitialState: function() {
		return {categories: [], categoryName: null, groups: []};
	},
	componentDidMount: function() {
	    this.loadCategoriesFromServer();
	},
	render: function() {
	    return (
	      <div className="categoryBox">
	        <h1>Categories</h1>
	        <CategoryList categories={this.state.categories} onCategorySelect={this.handleCategorySelect}/>
	        <CategoryForm onCategorySubmit={this.handleCategorySubmit} />
	        <GroupBox categoryName={this.state.categoryName} groups={this.state.groups} onGroupSubmit={this.handleGroupSubmit}/>
	        </div>
	    );
	}
});

// add it to global context so we can reference this from javascript.
window.CategoryBox = CategoryBox;

