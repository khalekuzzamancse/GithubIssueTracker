
## Add dependency inject(Koin)


## Coding convention

### Modules
 - `domain`
   - Define the abstraction by using `interface` or `abstract class` that should used by both `data` and `ui` module
   - It is kind of `Mediator` or separator between `:data` and `:ui` module because  `:data` modules does should not know about
     `:ui` module and vice versa but know about the `:domain` module
   - `Implentation` of defined `abstaction` will be provided in the `:data` module
  
### View 

- Function with suffix with preview `View` represent the piece of UI defined using `Composable`

### ViewData

- Class with suffix=`ViewData` represent the state or data used by that `View`
- These class should used within the UI layer only, the data they are got via `Model` class defined
  in `domain` layer is basically convert to `ViewData`
  because `domain` layer `Model` class should not directly used by other purpose or with other
  component to loose couple with the `Domain` module

### Controller and ViewModel

- Class with suffix=`Controller` represent the thing that manage the `State` and `Response` the
  event of particular `View`
    - It is can be used for both small and larger `View` or `Composable`
- Suffix with `ViewModel`preserve the state and response to event of a Route or destination
  level `View` or `Composable` so `ViewModel is a Controller`
    - Since the Route or destination level `View` or `Composable` is composed of small small
      destination level `Composable` so it
      preserve the `Controller` on configuration changed


### Package:Factory

- `package` with name=`factory` define the concrete classes for the `modules`
- It expose several `factory method` so that instance should be created using that method instead of
  directly use the `constructor`
  this allow to maintain the `single source of truth` for object creation and if later need to
  change provide different `Implementation` just need to
  change here, this will allow to code loose coupling and ..
### Packages , feature_x
- represent the feature `x`
- `x.components` define the small small component such as `UI` component or part of `UI`
- `x.route` represent the screen level `UI` such a screen or route that made of combining multiple `components`
