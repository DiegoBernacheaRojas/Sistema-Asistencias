// apiClient.js
import { getCookie } from './Util.js';

export async function apiRequest(url, method = 'GET', data = null) {
    const tokenadmin = getCookie('tokenadmin');

    const options = {
        method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${tokenadmin}`
        }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }
        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const result = await response.json();
        return result;
}