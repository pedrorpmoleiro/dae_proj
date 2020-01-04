<template>
    <v-content>
        <v-card>
            <!--<v-btn href="/#/welcome" class="ma-2" color="orange darken-2" dark>
                <v-icon dark left>mdi-arrow-left</v-icon>Back
            </v-btn>-->
        </v-card>
        <v-container fluid>
            <v-row align="center" justify="center">
                <v-col cols="6">
                    <v-card color="blue" dark>
                        <v-card-title color="white">User Profile</v-card-title>
                    </v-card>
                    <div class="form-group">
                        <v-text-field
                                disabled
                                label="Name"
                                name="NameProfile"
                                v-model="user.name"
                                prepend-icon="mdi-account"
                                id="NameProfile"
                                type="text"
                        />
                    </div>
                    <div class="form-group">

                        <v-text-field
                                label="Email"
                                v-model="user.email"
                                name="EmailProfile"
                                id="EmailProfile"
                                prepend-icon="mdi-at"
                                disabled
                                type="email"
                        />
                    </div>
                    <div class="form-group">
                        <v-text-field
                                label="Username"
                                v-model="user.username"
                                name="UsernameProfile"
                                id="UsernameProfile"
                                type="text"
                        />
                    </div>
                    <div class="form-group">
                        <v-text-field
                                label="Tipo"
                                v-model="user.tipo"
                                name="TipoProfile"
                                id="TipoProfile"
                                type="text"
                        />
                    </div>
                </v-col>
            </v-row>
        </v-container>
    </v-content>
</template>
<style scoped>
    img {
        max-height: 100px;
    }
</style>
<script type="text/javascript">
    export default {
        //props: ['user', 'wallets'],
        data() {
            return {
                user: {
                    name: undefined,
                    email: undefined,
                    tipo: undefined,
                    username: undefined
                },
            };
        },
        methods: {
        },
        mounted() {
            console.log(this.username);
        }
    };
</script>

<style scoped>
</style>
