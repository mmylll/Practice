# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "D:\JetBrains\CLion 2020.2.3\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "D:\JetBrains\CLion 2020.2.3\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = E:\SelfWork\mota

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = E:\SelfWork\mota\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/mota.dir/depend.make
# Include the progress variables for this target.
include CMakeFiles/mota.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/mota.dir/flags.make

CMakeFiles/mota.dir/main.c.obj: CMakeFiles/mota.dir/flags.make
CMakeFiles/mota.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=E:\SelfWork\mota\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/mota.dir/main.c.obj"
	D:\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\mota.dir\main.c.obj -c E:\SelfWork\mota\main.c

CMakeFiles/mota.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/mota.dir/main.c.i"
	D:\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E E:\SelfWork\mota\main.c > CMakeFiles\mota.dir\main.c.i

CMakeFiles/mota.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/mota.dir/main.c.s"
	D:\mingw64\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S E:\SelfWork\mota\main.c -o CMakeFiles\mota.dir\main.c.s

# Object files for target mota
mota_OBJECTS = \
"CMakeFiles/mota.dir/main.c.obj"

# External object files for target mota
mota_EXTERNAL_OBJECTS =

mota.exe: CMakeFiles/mota.dir/main.c.obj
mota.exe: CMakeFiles/mota.dir/build.make
mota.exe: CMakeFiles/mota.dir/linklibs.rsp
mota.exe: CMakeFiles/mota.dir/objects1.rsp
mota.exe: CMakeFiles/mota.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=E:\SelfWork\mota\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable mota.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\mota.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/mota.dir/build: mota.exe
.PHONY : CMakeFiles/mota.dir/build

CMakeFiles/mota.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\mota.dir\cmake_clean.cmake
.PHONY : CMakeFiles/mota.dir/clean

CMakeFiles/mota.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" E:\SelfWork\mota E:\SelfWork\mota E:\SelfWork\mota\cmake-build-debug E:\SelfWork\mota\cmake-build-debug E:\SelfWork\mota\cmake-build-debug\CMakeFiles\mota.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/mota.dir/depend

