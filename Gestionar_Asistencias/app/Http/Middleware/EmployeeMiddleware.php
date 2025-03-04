<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class EmployeeMiddleware 
{
    public function handle(Request $request, Closure $next)
    {
        if ($request->user() && $request->user()->role === 'employee') {
            return $next($request);
        }

        return response()->json(['error' => 'Unauthorized'], 403);
    }
}
