import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IntegerRange;

@SuppressWarnings("all")
public class MainCLI {
  public static void main(final String[] args) {
    final String systemName = "fs";
    final Mwe2Launcher launcher = new Mwe2Launcher();
    final String baseDir = "E:/E-Workspace/FileManager";
    final String modelName = "deepseek-v3.2-exp";
    IntegerRange _upTo = new IntegerRange(2, 2);
    for (final Integer i : _upTo) {
      {
        InputOutput.<String>println("======================================");
        InputOutput.<String>println((("正在启动 sample" + i) + ".mwe2..."));
        InputOutput.<String>println("======================================");
        final String mwe = (((("file://" + baseDir) + "/src/sample") + i) + ".mwe2");
        launcher.run(new String[] { mwe });
        InputOutput.<String>println((("sample" + i) + ".mwe2 启动完成"));
        final File srcGenPath = new File(((baseDir + "/src-gen/edu/") + systemName));
        final File targetPath = new File((((((baseDir + "/") + modelName) + "/") + systemName) + i));
        boolean _exists = srcGenPath.exists();
        if (_exists) {
          InputOutput.<String>println(((((((("正在拷贝 src-gen/edu/" + systemName) + " 到 ") + modelName) + "/") + systemName) + i) + "..."));
          MainCLI.copyDirectory(srcGenPath.toPath(), targetPath.toPath());
          InputOutput.<String>println("拷贝完成！");
        } else {
          InputOutput.<String>println((("警告：src-gen/edu/" + systemName) + " 不存在，跳过拷贝"));
        }
        InputOutput.println();
      }
    }
    InputOutput.<String>println("======================================");
    InputOutput.<String>println("所有任务完成！");
    InputOutput.<String>println("======================================");
  }

  /**
   * 递归拷贝目录
   */
  public static void copyDirectory(final Path source, final Path target) {
    try {
      final Consumer<Path> _function = (Path sourcePath) -> {
        try {
          final Path targetPath = target.resolve(source.relativize(sourcePath));
          boolean _isDirectory = Files.isDirectory(sourcePath);
          if (_isDirectory) {
            boolean _exists = Files.exists(targetPath);
            boolean _not = (!_exists);
            if (_not) {
              Files.createDirectories(targetPath);
            }
          } else {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      Files.walk(source).forEach(_function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
