export class userModel{
    user_id : number;
    user_name : string | undefined;
    email_id : string='';
    password : string | undefined;
    contact_number : string | undefined;

    constructor(){
        this.user_id=0
    }
}