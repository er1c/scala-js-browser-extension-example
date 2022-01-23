# Scala.js Browser Extension Example

Example [web browser extension](https://en.wikipedia.org/wiki/Browser_extension) in Scala.

This project uses:

* [ScalablyTyped](https://scalablytyped.org/docs/readme) to generate [Scala.js](https://www.scala-js.org/) interfaces for [Web Extension Polyfill](https://github.com/mozilla/webextension-polyfill), which is a cross-browser polyfill for browser extensions.
* [scalajs-react](https://github.com/japgolly/scalajs-react) for scala interfaces to [React](https://reactjs.org/)
* [scalacss](https://github.com/japgolly/scalacss) for type-safe CSS in Scala

## Example

```bash
sbt universal:packageBin
cd plugin/target/universal/
unzip plugin-0.1.0-SNAPSHOT.zip
```

The extracted `plugin-0.1.0-SNAPSHOT` folder can be loaded into chrome via `chrome://extensions/` and use `Load Unpacked` (ensure developer mode is turned on).

## TODO

- [x] Fix CI workflow
- [ ] Generate [index.html](plugin/src/universal/index.html) dynamically based upon `fastOptJS` vs `fullOptJS`.
- [ ] Split scala-js dependencies into separate `.js` files to improve compilation speed.
- [ ] Look into unit testing via [mockzilla-webextension](https://lusito.github.io/mockzilla-webextension/)
- [ ] Verify Firefox compatibility
- [ ] Add [background script](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/Anatomy_of_a_WebExtension#background_scripts) example
- [ ] Figure out `npm run dev` equivalent workflow
- [ ] Figure out/understand `asset-manifest.json` usage

## References

Original `React` inspiration from [Chrome Extension Kit](https://chromeextensionkit.com/)

## Contributing

The scala-js-browser-extension-example project welcomes contributions from anybody wishing to participate.  All code or documentation that is provided must be licensed with the same license that scala-js-browser-extension-example is licensed with (Apache 2.0, see [LICENCE](./LICENSE.md)).

People are expected to follow the [Scala Code of Conduct](./CODE_OF_CONDUCT.md) when discussing scala-js-browser-extension-example on GitHub, Gitter channel, or other venues.

Feel free to open an issue if you notice a bug, have an idea for a feature, or have a question about the code. Pull requests are also gladly accepted. For more information, check out the [contributor guide](./CONTRIBUTING.md).

## Copyright

Copyright 2022 Eric Peters & scala-js-browser-extension-example contributors.

## License

All code in this repository is licensed under the Apache License, Version 2.0.  See [LICENCE](./LICENSE.md).
