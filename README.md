# Reactive MVP Playground

Sample companheiro para minha apresentação / blog post 

`Evoluindo arquiteturas reativas`

[Slides](https://speakerdeck.com/ubiratansoares/evoluindo-arquiteturas-reativas)

[Blog](https://ubiratansoares.github.io/reactive-architectures)

Essa demo implementa uma chamada `GET` simples para [NumbersAPI](http://numbersapi.com/)

e ilustra uma série de idéias, dentre as quais

- Como estruturar as camadas de uma aplicação ao estilo de MVP/Clean de forma 100% reativa (com RxJava2), incluindo comportamentos segregados e modelos separados por camadas
- Uso de _Dependency Injection_ com as novas APIs da Dagger 2.11+, incluindo `AndroidInjector`, `@ContributesAndroidInjector` e afins
- Testes de unidade nas camadas nos níveis de unidade e integração, e como criar um robo de verificação de comportamentos para Presenters
- ETC


## Setup

Essa demo usa Android Studio / Android Gradle Plugin 3.0+ e é 100% escrita em Java

## TODO

- Alguns testes com Espresso
- Aplicar alguma estratégia para retenção de estado após mudança de configuração (LiveData)

## License
```
The MIT License (MIT)

Copyright (c) 2017 Ubiratan Soares

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```