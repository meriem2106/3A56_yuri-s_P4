package Services;

import java.util.List;

public interface Crud <S>{
    S create (S o);
    S update(S o);
    boolean delete (int o);
    S exists (S o);
    List<S> findAll();
    S findById(int Id);
}
