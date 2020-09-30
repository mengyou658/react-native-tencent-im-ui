require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-txim-ui"
  s.version          = package['version']
  s.summary          = package['description']
  s.description      = package['description']
  s.homepage         = package['homepage']
  s.license          = package['license']
  s.author           = package['author']
  s.source           = { :git => package['repository']['url'], :tag => s.version.to_s }

  s.ios.deployment_target = '9.0'
  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "TXIMSDK_TUIKit_iOS"
end

