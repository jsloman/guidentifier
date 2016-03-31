var todos = [{
  id: '_1',
  name: 'Buy some milk',
  done: true
}, {
  id: '_2',
  name: 'Birthday present to Alice',
  done: false
}];
 
var Todo = React.createClass({
  render: function() {
    var todo = this.props.todo;
    return (<li>{todo.name}<button>Done</button></li>);
  }
});
 
var TodoList = React.createClass({
  render: function() {
    var rows = this.props.todos.filter(function(todo) {
      return !todo.done;
    }).map(function(todo) {
      return (<Todo key={todo.id} todo={todo}></Todo>);
    });
    return (
      <div className="active-todos">
        <h2>Active</h2>
        <ul>{rows}</ul>
      </div>
    );
  }
});

var TodoForm = React.createClass({
	  getInitialState: function() {
	    return {
	      name: ''
	    };
	  },
	  handleNameChange: function(e) {
	    this.setState({
	      name: e.target.value
	    });
	  },
	  handleSubmit: function(e) {
		    e.preventDefault();
		    var name = this.state.name.trim();
		    // 1. Update data
		    TodoStorage.create(name, function() {
		      this.setState({
		        name: ''
		      });
		    }.bind(this));
		  },
	  render: function() {
	    var disabled = this.state.name.trim().length <= 0;
	    return (
	      <form onSubmit={this.handleSubmit}>
	        <input value={this.state.name} onChange={this.handleNameChange}></input>
	        <input type="submit" disabled={disabled}></input>
	      </form>
	    );
	  }
	});
 
var App = React.createClass({
	  getInitialState: function() {
	    return {
	      todos: []
	    };
	  },
	  componentDidMount: function() {// one of the lifecycle methods
	    var setTodos = function() {
	      TodoStorage.getAll(function(todos) {
	        this.setState({
	          todos: todos
	        });
	      }.bind(this));
	    }.bind(this);
	    // 2. Receive feedback
	    TodoStorage.on('change', setTodos);
	    setTodos();
	  },
	  render: function() {
	    return (
	      <div>
	        <h1>My Todo</h1>
	        <TodoList todos={this.state.todos}/>
	      </div>
	    );
	  }
	});
 
React.render(
  <App></App>,
  document.getElementById('app-container')
);