package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.TodoItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class TodoItemRepository extends AbstractJpaRepository<TodoItem, Long> {

    public TodoItemRepository() {
        super(TodoItem.class);
    }

//    public void update(TodoItem updatedTodoItem) {
//        Optional<TodoItem> optionalTodoItem = findOptional(updatedTodoItem.getId());
//        if (optionalTodoItem.isPresent()) {
//            TodoItem existingTodoItem = optionalTodoItem.get();
//            existingTodoItem.setName(updatedTodoItem.getName());
//            existingTodoItem.setComplete(updatedTodoItem.isComplete());
//            super.update(existingTodoItem);
//        }
//    }

}

