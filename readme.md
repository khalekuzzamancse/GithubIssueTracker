# Understanding the Project Structure

## Modules

### `:application`
- This module represents the conventional `:app` module
- It contains the `MainActivity`, `Manifest`, and other core app configurations
### `:build-logic`
- Contains the `Convention Plugin`
### `:core`
- This module contains submodules like `:network` that handle data fetching via `REST APIs`
- Feature-specific business logic modules, such as the `:data` module, will use it to fetch data

### `:common`
- This module holds common code and components shared across different `:feature` modules, such as common UI components

### `:feature`
- This is the parent module for individual features
- Features are organized as `submodules` under this module
- Typically, feature modules contain four submodules: `:domain`, `:data`, `:ui`, and `:di`

#### `:domain`
- Focuses on `Abstraction` over `Concretion`
- Acts as a `Mediator` or separator between the `:data` and `:ui` modules, preventing tight coupling
- The `abstraction` should be defined through `interfaces` or `abstract classes`, so no `concrete` implementations exist in this module
- It defines `Data Transfer Objects`  called `Model`, which represent the structure of data shared between the `:data` and `:ui` modules
- Client modules should not directly use the `Model` for their own purposes; instead, they should extract necessary data from it For example, the `:data` module shouldn't use it as an `Entity`, and the `:ui` module shouldn't use it directly as `state` for UI components This helps reduce coupling between the `:domain` module and its `clients`

#### `:data`
- Contains `business logic` and `implementation` details for what is defined in the corresponding `:domain` module
- This module includes `Entities`, which represent the `JSON` format of `API responses`
- `Entities` should be `internal` to this module and not directly used by `client modules` To provide data to client modules, extract the necessary information from the `Entity`, build the `Model`, and pass it on

#### `:di`
- Represents the `dependency injection` container
- This module includes `Factory  methods` that provide concrete implementations for abstractions defined in the `:domain` module but implemented in the `:data` module
- It maintains a `single source of truth` for object or instance creation, allowing easy changes in `implementation` by modifying the `Factory method` without touching client code

## Keywords or Suffixes

### View
- Functions with the `View` suffix represent pieces of UI defined using `Composable`

### ViewData
- Classes with the `ViewData` suffix represent the `state` or data used by a `View`
- These classes should only be used within the `:ui` module The data they receive from the `Model` class is converted into `ViewData` because the `Model` class should not be directly used for other purposes to ensure loose coupling with the `:domain` module

### Controller and ViewModel
- Classes with the `Controller` suffix `manage` the `state` and respond to `events` for a particular `View` These can be used for both small and large views
- Classes with the `ViewModel` suffix manage the state and respond to events for a Route or destination-level `View`Since these Route-level views are composed of smaller `Composable/View` components, the `ViewModel` serves as a `Controller` and preserves the state across configuration changes

### Package: `.factory`
- A `package` named `factory` defines the concrete classes for the modules
- It exposes several `Factory methods`, ensuring that instances are created using these methods instead of direct constructors This helps maintain a `single source of truth` for object creation and allows easy changes to the implementation, ensuring loose coupling

### Packages: `feature_x`
- Represents feature `x`
- The `x.components` package defines smaller components, such as UI elements or parts of the UI
- The `x.route` package represents screen-level UI, such as screens or routes composed of multiple components
