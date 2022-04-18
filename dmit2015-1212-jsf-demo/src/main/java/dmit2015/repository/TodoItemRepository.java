package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.TodoItem;
import dmit2015.security.RepositorySecurityInterceptor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.interceptor.Interceptors;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
@Interceptors({RepositorySecurityInterceptor.class})
public class TodoItemRepository extends AbstractJpaRepository<TodoItem, Long> {

    public TodoItemRepository() {
        super(TodoItem.class);
    }

    public void create(TodoItem newTodoItem) {
        super.create(newTodoItem);
    }

}

