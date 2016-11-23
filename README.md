# zagar_io

## HIBERNATE
H2 in-memo db for tests 

@see dao.database
@see hibernate.cfg.xml
@see dao

## RESPONSE FORMAT
Jackson API

@see model.response
@see model.mixin

## Authenticated users

services.TokenService provides \<Token,Profile\> Map and some handy methods
services.AccountService provides all accounting stuff (email, logout, etc)
