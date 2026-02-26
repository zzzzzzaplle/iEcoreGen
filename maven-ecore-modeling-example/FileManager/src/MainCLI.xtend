import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.nio.file.Path

class MainCLI {
	def static void main(String[] args) {
		// *** 请在此处填写系统名称 ***
		val systemName = "fs"  // TODO: 根据实际情况修改系统名称，如 fs, carrental, videorental 等
		
		val launcher = new Mwe2Launcher()
		val baseDir = "E:/E-Workspace/FileManager"
		val modelName = "deepseek-v3.2-exp"
		// 循环启动 sample1.mwe2 到 sample5.mwe2
		for (i : 2..2) {
			println("======================================")
			println("正在启动 sample" + i + ".mwe2...")
			println("======================================")
			// 启动 mwe2 文件
			val mwe = 'file://' + baseDir + '/src/sample' + i + '.mwe2'
			launcher.run(#[mwe])
			println("sample" + i + ".mwe2 启动完成")
			
			// 拷贝生成的代码：src-gen/edu/{systemName} -> deepseek-r1/{systemName}{i}
			val srcGenPath = new File(baseDir + "/src-gen/edu/" + systemName)
			val targetPath = new File(baseDir + "/" + modelName + "/" + systemName + i)
			
			if (srcGenPath.exists()) {
				println("正在拷贝 src-gen/edu/" + systemName + " 到 " + modelName + "/" + systemName + i + "...")
				copyDirectory(srcGenPath.toPath(), targetPath.toPath())
				println("拷贝完成！")
			} else {
				println("警告：src-gen/edu/" + systemName + " 不存在，跳过拷贝")
			}
			
			println()
		}
		
		println("======================================")
		println("所有任务完成！")
		println("======================================")
	}
	
	/**
	 * 递归拷贝目录
	 */
	def static void copyDirectory(Path source, Path target) {
		Files.walk(source).forEach[sourcePath |
			val targetPath = target.resolve(source.relativize(sourcePath))
			if (Files.isDirectory(sourcePath)) {
				if (!Files.exists(targetPath)) {
					Files.createDirectories(targetPath)
				}
			} else {
				Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING)
			}
		]
	}
}