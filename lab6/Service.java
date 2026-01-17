package lab6;

/**
 * Пример сервиса с кешируемыми областями
 */
@Cache({"users", "profiles", "sessions"})
public class Service {
}