# BKCommonLib
[Spigot Resource Page](https://www.spigotmc.org/resources/bkcommonlib.39590/) | [Dev Builds](https://ci.mg-dev.eu/job/BKCommonLib/) | [Javadocs](https://ci.mg-dev.eu/javadocs/BKCommonLib/)

To build BKCommonLib you will (probably) need to run [Build Tools](https://www.spigotmc.org/wiki/buildtools/) beforehand.
Otherwise tests will fail and maven will complain. No actual server code is linked during compiling, hence the dependency is type test.

This is a library-plugin system, introducing a lot of utility classes
It also simplifies coding:
* PluginBase: allows quick registering and monitoring of plugins being enabled
* (Async)Task: allows quick task starting and dynamic in-code creation
* Utilities for virtually every needed task ahead, from mathematics to obtaining an entity by UUID
* Custom node-based configuration extension
* BlockLocation, BlockMap and BlockSet to safely use blocks in maps and sets
* ItemParser class to generate a parser from user and use it during item transactions
* Nanosecond StopWatch class for performance monitoring
* Operation class to handle entities, players, chunks and worlds the way you want it

### Cloud Command Framework
BKCommonLib also shades in the Cloud Command Framework, and adds some useful helper classes.
Read more about it [here](CLOUD_HOWTO.md).

### License
MIT License

Copyright (C) 2013-2015 bergerkiller Copyright (C) 2016-2020 Berger Healer

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, and/or sublicense the Software,  
and to permit persons to whom the Software is furnished to do so, subject to  
the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.
