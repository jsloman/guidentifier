"use strict";
  
var GroupForm = React.createClass({
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
	    this.props.onGroupSubmit(this.props.categoryName, name);
		this.setState({name: ''});
	},
	render: function() {
		if (this.props.categoryName == null) {
			return (
					<div className="emptyGroupForm"></div>
			);
		}
			
		return (
	      <form className="groupForm" onSubmit={this.handleSubmit}>
	        <input
	          type="text"
	          placeholder="Group name"
	          value={this.state.name}
	          onChange={this.handleNameChange}
	        />
	        <input type="submit" value="Add group" />
	      </form>
	    );
	}
});

var GroupBox = React.createClass({
	render: function() {
	    var groupNodes = this.props.groups.map(function(group) {
	      return (
	    		<div key={group.name} className="group">
					{group.name}
				</div>
	      );
	    });
	    return (
	      <div className="groupBox">
	        <h1>Groups</h1>
	        <div className="groupList">
	          {groupNodes}
	        </div>
	        <GroupForm categoryName={this.props.categoryName} onGroupSubmit={this.props.onGroupSubmit}/>
	      </div>
	    );
	}
});
