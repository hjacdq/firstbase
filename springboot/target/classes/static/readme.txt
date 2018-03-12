logging.config                    # 日志配置文件路径，如 classpath:logback-spring.xml
logging.exception-conversion-word # 记录异常时使用的转换词
logging.file                      # 记录日志的文件名称，如：test.log
logging.level.*                   # 日志映射，如：logging.level.root=WARN，logging.level.org.springframework.web=DEBUG
logging.path                      # 记录日志的文件路径，如：d:/
logging.pattern.console           # 向控制台输出的日志格式，只支持默认的 logback 设置。
logging.pattern.file              # 向记录日志文件输出的日志格式，只支持默认的 logback 设置。
logging.pattern.level             # 用于呈现日志级别的格式，只支持默认的 logback 设置。
logging.register-shutdown-hook    # 初始化时为日志系统注册一个关闭钩子