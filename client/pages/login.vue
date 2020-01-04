<template>
    <v-content>
        <v-card color="indigo lighten-1" dark>
            <v-card-title color="white">
                Apoio à gestão de clubes desportivos
            </v-card-title>
        </v-card>
        <v-container class="fill-height" fluid>
            <v-row align="center" justify="center">
                <v-col cols="12" sm="8" md="4">
                    <v-card class="elevation-12">
                        <v-toolbar color="indigo lighten-1" dark flat>
                            <v-toolbar-title>Login </v-toolbar-title>
                        </v-toolbar>
                        <v-card-text>
                            <v-form>
                                <v-text-field label="Username" v-model="username" name="UsernameLogin" prepend-icon="mdi-account" type="text"/>
                                <v-text-field id="password" v-model="password" label="Password" name="passwordLogin" prepend-icon="mdi-lock"
                                              type="password"/>
                            </v-form>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer/>
                            <v-btn color="indigo lighten-1"  class="white--text"@click="logIn">Login</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </v-content>
</template>
<script>
    const crypto = require('crypto');
    export default {
        data: function () {
            return {
                username: undefined,
                password: undefined
            }
        },
        methods: {
            logIn() {
                const hash = crypto.createHash('sha256');
                hash.update(this.password);
                var passwordHash = hash.digest('base64');
               // console.log(passwordHash);
                /* axios.post('api/login',{emil:username,password:pass})
                     .then(response=>{console.log('LOGIN')})
                     .catch(error=>{alert("No estas registrado habla bien")})*/
                var data = JSON.stringify({
                    username: this.username,
                    password: this.password
                })
                //console.log(data)
                axios({method: 'post', url: 'api/users/login', data: data, headers: {'Content-Type': 'application/json'}})
                    .then(response => {
                        console.log('LOGIN')
                        //  this.$root.token=response.data.access_token;
                        localStorage.token = response.data.access_token
                        this.$router.push('/welcome')
                        //window.axios.defaults.headers.common['Authorization']=`Bearer ${localStorage.token}`

                    })
                    .catch(error => {
                        console.log(error)
                        console.log(axios.defaults.headers)
                    })

            }
        }
    }
</script>
<style scoped>

</style>