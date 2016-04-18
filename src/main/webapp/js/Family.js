"use strict";

var FamilyForm = React.createClass({
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
	    this.props.onFamilySubmit(this.props.typeId, name);
		this.setState({name: ''});
	},
	render: function() {
		if (this.props.typeId == null) {
			return (
					<div className="emptyFamilyForm"></div>
			);
		}
			
		return (
	      <form className="familyForm" onSubmit={this.handleSubmit}>
	        <input
	          type="text"
	          placeholder="Family name"
	          value={this.state.name}
	          onChange={this.handleNameChange}
	        />
	        <input type="submit" value="Add family" />
	      </form>
	    );
	}
});

var FamilyBox = React.createClass({
	render: function() {
	    var familyNodes = this.props.families.map(function(family) {
	      return (
	    		<div key={family.id} className="family">
					{family.name}
				</div>
	      );
	    });
	    return (
	      <div className="familyBox">
	        <h1>Families</h1>
	        <div className="familyList">
	          {familyNodes}
	        </div>
	        <FamilyForm typeId={this.props.typeId} onFamilySubmit={this.props.onFamilySubmit}/>
	      </div>
	    );
	}
});
